package com.amdigital.sales;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.wait.CassandraQueryWaitStrategy;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;

public class TestEnvironment implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

	  @Container
	  private static final CassandraContainer cassandra =
	      (CassandraContainer) new CassandraContainer("cassandra:3.10")
	          .withInitScript("loadCassandra.cql").withExposedPorts(9042)
	          .waitingFor(new CassandraQueryWaitStrategy())
	          .withImagePullPolicy(PullPolicy.defaultPolicy());

	  ;

	  @Override
	  public void beforeAll(ExtensionContext context) {
	    cassandra.addEnv("CASSANDRA_DC", "DC1");
	    cassandra.addEnv("CASSANDRA_ENDPOINT_SNITCH", "GossipingPropertyFileSnitch");
	    cassandra.start();

	    String address = cassandra.getContainerIpAddress();
	    Integer port = cassandra.getMappedPort(9042);

	    System.setProperty("cassandracontactpoints", address + ":" + port);
	    System.setProperty("cassandrausername", "username");
	    System.setProperty("cassandrapassword", "password");
	    System.setProperty("cassandradefaultkeyspace", "amdigital");
	    System.setProperty("cassandradatacenter", "DC1");
	  }

	  @Override
	  public void close() {
	    cassandra.stop();
	  }

}
