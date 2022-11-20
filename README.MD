1. Springboot版本问题
- Spring Boot 2.7中不推荐使用/META-INF/spring.factories文件，
- 并且在Spring Boot 3将移除对/META-INF/spring.factories的支持。 新的写法是创建一个新的文件：
  /META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 内容直接放配置类即可（注意有spring目录），比如这样：
  com.example.swagger.SwaggerAutoConfiguration


2. 理解
    1. 其实就是组件的切入spring的入口，组件中的Bean想被Spring管理，这是一个入口，通过它来引入其他的类通过@bean进行
3