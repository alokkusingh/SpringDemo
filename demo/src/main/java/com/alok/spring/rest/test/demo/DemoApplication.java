//https://spring.io/guides/tutorials/bookmarks/
//Selcted
//	Web
//  JPA
//  H2
package com.alok.spring.rest.test.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.alok.spring.rest.test.demo.jpa.AccountRepository;
import com.alok.spring.rest.test.demo.jpa.BookmarkRepository;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }

	
	public static void main(String[] args) {
		// the below is to disable auto restart on classpath file changes
		//System.setProperty("spring.devtools.restart.enabled", "false");
		
		/*
		 * Classes that can be used to bootstrap and launch a Spring application
		 * from a Java main method. 
		 * By default class will perform the following steps to bootstrap your application:
		 * Create an appropriate ApplicationContext instance (depending on your classpath) 
		 * Register a CommandLinePropertySource to expose command
		 * line arguments as Spring properties Refresh the application context,
		 * loading all singleton beans Trigger any CommandLineRunner beans
		 */
		SpringApplication.run(DemoApplication.class, args);
		
		/*
drop table account if exists;                                                                                                                       
drop table acrole if exists;                                                                                                                        
drop table acuser if exists;                                                                                                                        
drop table acuser_acrole if exists;                                                                                                                 
drop table bookmark if exists;                                                                                                                      
create table account (id bigint generated by default as identity, password varchar(255), username varchar(255), primary key (id));                  
create table acrole (id bigint generated by default as identity, description varchar(255), role varchar(255), primary key (id));                    
create table acuser (id bigint generated by default as identity, password binary(255), username varchar(255), primary key (id));                    
create table acuser_acrole (acuser_id bigint not null, acrole_id bigint not null, primary key (acuser_id, acrole_id));                              
create table bookmark (id bigint generated by default as identity, description varchar(255), uri varchar(255), account_id bigint, primary key (id));
alter table acuser add constraint UK_l7itcqq4j7pr5p54t2oyuy7x5 unique (username);                                                                   
alter table acuser_acrole add constraint FKd215lqjp91h1sq5e0lp0929d9 foreign key (acrole_id) references acrole;                                     
alter table acuser_acrole add constraint FKs7rm89wnvmf9ol6aq9ow7dxdh foreign key (acuser_id) references acuser;                                     
alter table bookmark add constraint FKdld3dxpefeqpr9rxyef5idjg4 foreign key (account_id) references account;
insert into acuser (username, password) values ('alok', '616c6f6b313233');
insert into acrole (role, description) values ('user','User role');
insert into acrole (role, description) values ('admin','Admin role');

insert into acuser_acrole values (1,1);
insert into acuser_acrole values (1,2);
select * from acuser;
select * from acrole;
select * from acuser_acrole;   
		*/
	}

/*	@Bean
	CommandLineRunner init(final AccountRepository accountRepository, final BookmarkRepository bookmarkRepository) {
		
		return new CommandLineRunner() {
			Account account;

			@Override
			public void run(String... arg0) throws Exception {
				for (String a : "alok,rachna,avinash,prashant,richa,mfisher,mpollack,jlong".split(",")) {
					account = accountRepository.save(new Account(a, "password"));
					bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
					bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));
				}
				
			}
			
		};


	}*/

}
