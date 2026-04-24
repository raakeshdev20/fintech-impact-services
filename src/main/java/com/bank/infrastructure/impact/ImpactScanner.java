package com.bank.infrastructure.impact;

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
        Set<String> tags = new HashSet<>();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(new File("./.git"))
                .readEnvironment()
                .findGitDir()
                .build();

        try (Git git = new Git(repository)) {
            // Fix: We now use the helper method to turn the branch name into a TreeIterator
            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, targetBranch);

            // This compares the target branch to your current "Index" (staged changes)
            List<DiffEntry> diffs = git.diff()
                    .setOldTree(oldTreeParser)
                    .call();

            for (DiffEntry entry : diffs) {
                String path = entry.getNewPath();
                if (path.contains("payments")) tags.add("@payments");
                if (path.contains("transfers")) tags.add("@transfers");
                if (path.contains("transactions")) tags.add("@transactions");
            }
        }

        if (tags.isEmpty()) tags.add("@smoke");
        return tags;
    }

    // Helper method to convert a branch name into something JGit can iterate over
    private AbstractTreeIterator prepareTreeParser(Repository repository, String ref) throws Exception {
        try (ObjectReader reader = repository.newObjectReader()) {
            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            treeParser.reset(reader, repository.resolve(ref + "^{tree}"));
            return treeParser;
        }
    }
}