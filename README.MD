1. 
Springboot版本问题
Spring Boot 2.7中不推荐使用/META-INF/spring.factories文件，
并且在Spring Boot 3将移除对/META-INF/spring.factories的支持。

新的写法是创建一个新的文件：

/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

内容直接放配置类即可（注意有spring目录），比如这样：

com.example.swagger.SwaggerAutoConfiguration
