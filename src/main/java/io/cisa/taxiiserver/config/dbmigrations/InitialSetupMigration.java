package io.cisa.taxiiserver.config.dbmigrations;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

import io.cisa.taxiiserver.config.Constants;
import io.cisa.taxiiserver.domain.ApiRoot;
import io.cisa.taxiiserver.domain.Authority;
import io.cisa.taxiiserver.domain.Collection;
import io.cisa.taxiiserver.domain.Discovery;
import io.cisa.taxiiserver.domain.User;
import io.cisa.taxiiserver.security.AuthoritiesConstants;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
    public void addUsers(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        User systemUser = new User();
        systemUser.setId("user-0");
        systemUser.setLogin("system");
        systemUser.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
        systemUser.setFirstName("");
        systemUser.setLastName("System");
        systemUser.setEmail("system@localhost");
        systemUser.setActivated(true);
        systemUser.setLangKey("en");
        systemUser.setCreatedBy(systemUser.getLogin());
        systemUser.setCreatedDate(Instant.now());
        systemUser.getAuthorities().add(adminAuthority);
        systemUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(systemUser);

        User anonymousUser = new User();
        anonymousUser.setId("user-1");
        anonymousUser.setLogin("anonymoususer");
        anonymousUser.setPassword("$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO");
        anonymousUser.setFirstName("Anonymous");
        anonymousUser.setLastName("User");
        anonymousUser.setEmail("anonymous@localhost");
        anonymousUser.setActivated(true);
        anonymousUser.setLangKey("en");
        anonymousUser.setCreatedBy(systemUser.getLogin());
        anonymousUser.setCreatedDate(Instant.now());
        mongoTemplate.save(anonymousUser);

        User adminUser = new User();
        adminUser.setId("user-2");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("en");
        adminUser.setCreatedBy(systemUser.getLogin());
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(adminUser);

        User userUser = new User();
        userUser.setId("user-3");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("en");
        userUser.setCreatedBy(systemUser.getLogin());
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(userUser);
    }
    
    @ChangeSet(order = "03", author = "initiator", id = "03-addApiRoot")
    public void addApiRoot(MongoTemplate mongoTemplate) {
    	ApiRoot apiRoot = new ApiRoot();
    	apiRoot.setDisplayName("cisa");
    	apiRoot.setDescription("CISA TAXII Service v0.01. A Cyber Incidence Situation Awareness research working group setup.");
    	Set<String> versions = new HashSet<>();
    	versions.add("taxii-2.0");
    	apiRoot.versions(versions);
    	apiRoot.setLastModifiedDate(ZonedDateTime.now());
    	apiRoot.setMaxContentLength(50000000);

    	Set<String> mediaTypes = new HashSet<>();
    	mediaTypes.add(Constants.ACCEPT_STIX_HEADER);
    	
    	Collection sensor = new Collection();
    	sensor.setCanRead(true);
    	sensor.setCanWrite(true);
    	sensor.setDescription("CISA Automated Sensor Collection");
    	sensor.setDisplayName("CISA Sensor Collector");
    	sensor.setUrl("/cisa/collections/sensor");    	
    	
    	
    	sensor.setMediaTypes(mediaTypes);
    	
    	Collection operation = new Collection();
    	operation.setCanRead(true);
    	operation.setCanWrite(true);
    	operation.setDescription("CISA Automated Sensor Collection");
    	operation.setDisplayName("CISA Operation Collector");
    	operation.setUrl("/cisa/collections/operation");
    	operation.setMediaTypes(mediaTypes);
    	
    	
    	Set<Collection> collections = new HashSet<>();
    	collections.add(sensor);
    	collections.add(operation);
    	apiRoot.setCollections(collections);
    	
    	mongoTemplate.save(collections);
    	
    }


    @ChangeSet(order = "04", author = "initiator", id = "04-addDiscovery")
    public void addDiscovery(MongoTemplate mongoTemplate) {
    	Discovery discovery = new Discovery();
    	discovery.setDisplayName("CISA TAXII Server");
    	discovery.setDescription("CISA TAXII Server for sensor and operation data");
    	discovery.setDefaultURL("cisa");
    	discovery.setContact("CISA Research working group.");
    	Set<String> apiRoots = new HashSet<>();
    	apiRoots.add("/cisa");
    	apiRoots.add("/taxii");
    	discovery.setApiRoots(apiRoots);
    	mongoTemplate.save(discovery);
    	
    	apiRoots.clear();
    	apiRoots.add("/taxii");
    	discovery.setApiRoots(apiRoots);
    	discovery.setDefaultURL("taxii");
    	mongoTemplate.save(discovery);    	
    }
}
