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

            List<String> changedFiles = diffs.stream()
                    .map(DiffEntry::getNewPath)
                    .toList();


            System.out.println("JGit found changed files: " + changedFiles);

            return calculateTags(changedFiles);
        }
    }

    public Set<String> calculateTags(List<String> changedFiles) {
        Set<String> tags = new HashSet<>();

        for (String file : changedFiles) {
            // Standardize the path for cross-platform (Windows vs Linux) consistency
            String path = file.replace("\\", "/").toLowerCase();

            // If it's the build config OR in the auth/security package, run EVERYTHING.
            if (path.endsWith("pom.xml") || path.contains("/auth/") ) {
                System.out.println("CRITICAL: Global or Security impact [" + path + "]. Escalating to @regression.");
                return Set.of("@regression");
            }

            // 2. DOMAIN-SPECIFIC MAPPING (Path-Based)
            // We look for the folder name in the package structure.
            if (path.contains("/payments/")) {
                tags.add("@payments");
            }
            if (path.contains("/transfer/")) {
                tags.add("@transfers");
            }
            if (path.contains("/transactions/")) {
                tags.add("@transactions");
            }
        }

        // 3. FALLBACK (The "Lean" Logic)
        // If files changed but none were in our code packages (like README or .gitignore)
        if (tags.isEmpty() && !changedFiles.isEmpty()) {
            System.out.println("Non-functional change detected. Falling back to @smoke.");
            tags.add("@smoke");
        }

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