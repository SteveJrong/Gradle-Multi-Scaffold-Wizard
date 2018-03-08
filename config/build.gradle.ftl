/**
 * 构建配置文件 - ${fmProjectName}
 */
apply plugin: 'java'
apply plugin: 'eclipse'

repositories {
    maven { url "${fmRepositories}" }
}

tasks.withType(JavaCompile) {
    options.encoding = '${fmEncoding}'
}

[compileJava, compileTestJava]*.options*.encoding = '${fmEncoding}'

dependencies {
    testImplementation 'junit:junit:4.12'
}

configurations.all {
	transitive = ${fmTransitive}
}

allprojects {
	repositories {
	    maven { url "${fmRepositories}" }
	}
}

subprojects {
}