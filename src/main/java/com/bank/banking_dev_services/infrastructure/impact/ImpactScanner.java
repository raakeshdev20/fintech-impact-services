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
        // 1. Initialize the JGit Repository
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(new File("./.git"))
                .readEnvironment()
                .findGitDir()
                .build();

        try (Git git = new Git(repository)) {
            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, targetBranch);

            // 2. Get the list of changed files
            List<DiffEntry> diffs = git.diff()
                    .setOldTree(oldTreeParser)
                    .call();

            // 3. Convert DiffEntries to a simple list of file paths (Strings)
            List<String> changedFiles = diffs.stream()
                    .map(DiffEntry::getNewPath)
                    .toList();

            // 4. Call  new calculation logic
            return calculateTags(changedFiles);
        }
    }

    public Set<String> calculateTags(List<String> changedFiles) {
        Set<String> tags = new HashSet<>();

              for (String file : changedFiles) {
            // Note: Use lowercase to ensure matches are consistent
            String lowerFile = file.toLowerCase();

            if (lowerFile.contains("payments")) {
                tags.add("@payments");
            }
            if (lowerFile.contains("transfers")) {
                tags.add("@transfers");
            }
            if (lowerFile.contains("transactions")) {
                tags.add("@transactions");
            }

        }
        if (tags.isEmpty() && !changedFiles.isEmpty()) {
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