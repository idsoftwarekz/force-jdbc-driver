package kz.idsoftware.force.jdbc.integration;

public class AbstractIntegrationTest {

  protected String url = System.getenv("FORCE_URL");
  protected String user = System.getenv("FORCE_USER");
  protected String password = System.getenv("FORCE_PASSWORD");
  protected String token = System.getenv("FORCE_TOKEN");

}
