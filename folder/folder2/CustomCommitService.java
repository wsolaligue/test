package crawler.common.github.service;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_COMMITS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;

import java.io.IOException;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.service.CommitService;

import crawler.common.github.repository.CustomRepositoryCommit;

public class CustomCommitService extends CommitService {

  public CustomCommitService() {
    super();
  }
  
  public CustomCommitService(GitHubClient client) {
    super(client);
  }
  
  public CustomRepositoryCommit getCustomCommit(IRepositoryIdProvider repository, String sha)
          throws IOException {
    String id = getId(repository);
    if (sha == null)
      throw new IllegalArgumentException("Sha cannot be null"); //$NON-NLS-1$
    if (sha.length() == 0)
      throw new IllegalArgumentException("Sha cannot be empty"); //$NON-NLS-1$

    StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
    uri.append('/').append(id);
    uri.append(SEGMENT_COMMITS);
    uri.append('/').append(sha);
    GitHubRequest request = createRequest();
    request.setUri(uri);
    request.setType(CustomRepositoryCommit.class);
    return (CustomRepositoryCommit) client.get(request).getBody();
  }
}
