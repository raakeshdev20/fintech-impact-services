package com.bank.banking_dev_services.infrastructure.impact;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImpactScanner {

    public Set<String> getImpactedTags(String targetBranch) throws Exception {

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.readEnvironment()
                .findGitDir(new File("."))
                .build();
        try (Git git = new Git(repository)) {
            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, targetBranch);
            List<DiffEntry> diffs = git.diff()
                    .setOldTree(oldTreeParser)
                    .call();
            Set<String> tags = calculateTags(diffs);

            if (tags.isEmpty()) {
                System.out.println("DECISION: No impactful changes detected → fallback @smoke");
                return Set.of("@smoke");
            }

            System.out.println("FINAL DECISION: impacted tags = " + tags);
            return tags;
        }
    }

    public Set<String> calculateTags(List<DiffEntry> diffs) {

        Set<String> tags = new HashSet<>();

        System.out.println("=== IMPACT ANALYSIS START ===");

        for (DiffEntry entry : diffs) {

            String newPath = entry.getNewPath() != null ? entry.getNewPath().toLowerCase() : "";
            String oldPath = entry.getOldPath() != null ? entry.getOldPath().toLowerCase() : "";

            System.out.println("Evaluating change:");
            System.out.println("OLD PATH: " + oldPath);
            System.out.println("NEW PATH: " + newPath);
            System.out.println("CHANGE TYPE: " + entry.getChangeType());

            // =========================
            // CRITICAL / GLOBAL RULE
            // =========================
            if (newPath.contains("/auth/")
                    || oldPath.contains("/auth/")
                    || newPath.endsWith("pom.xml")) {

                System.out.println(
                        "DECISION: Critical security or global impact detected → @regression"
                );

                System.out.println("=== IMPACT ANALYSIS END (EARLY EXIT) ===");
                return Set.of("@regression");
            }

            // =========================
            // DOMAIN RULES
            // =========================
            if (newPath.contains("/payments/") || oldPath.contains("/payments/")) {
                System.out.println("DECISION: Payments module impacted → @payments");
                tags.add("@payments");
            }

            if (newPath.contains("/transfer/") || oldPath.contains("/transfer/")) {
                System.out.println("DECISION: Transfer module impacted → @transfers");
                tags.add("@transfers");
            }

            if (newPath.contains("/transactions/") || oldPath.contains("/transactions/")) {
                System.out.println("DECISION: Transactions module impacted → @transactions");
                tags.add("@transactions");
            }
        }

        System.out.println("=== IMPACT ANALYSIS END ===");
        return tags;
    }

    private AbstractTreeIterator prepareTreeParser(Repository repository, String ref) throws Exception {

        try (ObjectReader reader = repository.newObjectReader()) {

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            treeParser.reset(reader, repository.resolve(ref + "^{tree}"));

            return treeParser;
        }
    }
}