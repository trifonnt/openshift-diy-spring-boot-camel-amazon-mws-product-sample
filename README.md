# Prerequisite 01 - Openshift Account

Before we can start building the application, we need to have an OpenShift free or paid account and RedHat client tools(rhc) installed. For instructions how to install rhc please read [Getting Started with OpenShift Online](https://developers.openshift.com/en/getting-started-overview.html).


# Prerequisite 02 - Amazon MWS libraries

We must download Amazon MWS Product library and import it into Local Maven repository.
```shell
mkdir amazon-mws-products
cd amazon-mws-products

wget https://images-na.ssl-images-amazon.com/images/G/01/mwsportal/clientlib/Products/2011-10-01/MWSProductsJavaClientLibrary-2011-10-01._V269521071_.zip
unzip ./MWSProductsJavaClientLibrary-2011-10-01._V269521071_.zip


mvn install:install-file -Dfile=dist/MWSClientJavaRuntime-1.0.jar -DgroupId=name.trifon.amazon.mws -DartifactId=mws-client-runtime -Dversion=1.0.0 -Dpackaging=jar

mvn install:install-file -Dfile=dist/MWSProducts_2011-10-01_v2016-06-01.jar -DgroupId=name.trifon.amazon.mws -DartifactId=mws-java-product -Dversion=1.0.0 -Dpackaging=jar
```


# Step 1: Create DIY application
Setup connection to OpenShift Broker

	rhc setup --server openshift.redhat.com -l user@mail.com

To create an application using client tools, type the following command:

	rhc app create camel diy-0.1


This command creates an application *camel* using *DIY* cartridge and clones the repository to *camel* directory.

# Step 2: Add PostgreSQL cartridge to application

The application we are creating will use PostgreSQL database, hence we need to add appropriate cartridge to the application:

	rhc cartridge add postgresql-9.2 --app camel

After creating the cartridge, it is possible to check its status with the following command:

	rhc cartridge status postgresql-9.2 --app camel

# Step 3: Delete Template Application Source code

OpenShift creates a template project that can be freely removed:

	cd camel
	git rm -rf .openshift README.md diy misc


Configure GIT:

	git config --global user.email "you@example.com"
	git config --global user.name "Your Name"

Commit the changes:

	git commit -am "Removed template application source code"

# Step 4: Pull Source code from GitHub

    git remote add upstream https://github.com/trifonnt/openshift-diy-spring-boot-camel-sample.git
    git pull -s recursive -X theirs upstream master

# Step 5: Push changes

The basic template is ready to be pushed:

	git push

The initial deployment (build and application startup) will take some time (up to several minutes). Subsequent deployments are a bit faster, although starting Spring Boot application may take even more than 2 minutes on small Gear:

	Tomcat started on port(s): 8080/http
	Started Application in 125.511 seconds

You can now browse to: http://camel-<namespace>.rhcloud.com/manage/health and you should see:

	{
		"status": "UP",
		"database": "PostgreSQL",
		"hello": 1
	}

You can then browse to "/" to see the API root resource.

# Step 6: Adding Jenkins

Using Jenkins has some advantages. One of them is that the build takes place in it's own Gear. To build with Jenkins, OpenShift needs a server and a Jenkins client cartridge attached to the application. Creating Jenkins application:

	rhc app create ci jenkins

And attaching Jenkins client to the application:

	rhc cartridge add jenkins-client --app camel

You can now browse to: http://ci-<namespace>.rhcloud.com and login with the credentials provided. When you make next changes and push them, the build will be triggered by Jenkins:

	remote: Executing Jenkins build.
	remote:
	remote: You can track your build at https://ci-<namespace>.rhcloud.com/job/boot-build
	remote:
	remote: Waiting for build to schedule.........

And when you observe the build result, the application starts a bit faster on Jenkins:

	Started Application in 52.391 seconds

# Under the hood

http://camel.apache.org/spring-boot.html
