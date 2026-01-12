package com.yuansaas.user.auth.security;

import com.yuansaas.core.environment.EnvironmentUtil;
import com.yuansaas.user.auth.filter.JwtAuthFilter;
import com.yuansaas.user.auth.security.annotations.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * 安全配置
 *
 * @author HTB 2025/8/11 15:11
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .cors(cors -> cors.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // 放行认证接口
                        .requestMatchers("/wx/mp/callback/**").permitAll()
                        .requestMatchers("/wx/mp/auth/qrcode/temp/url").permitAll()
                        .anyRequest()
                                .permitAll()
//                                .authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(corsHeaderOverrideFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthorizationManagerBeforeMethodInterceptor securityAuthInterceptor(SecurityService securityService) {
        SecurityAuthAuthorizationManager manager = new SecurityAuthAuthorizationManager(securityService);
        return AuthorizationManagerBeforeMethodInterceptor.preAuthorize(manager);
    }

    /**
     * 这个过滤器会清除所有现有的 CORS 头，然后重新设置正确的头
     * 要放在 CorsFilter 之前执行
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public OncePerRequestFilter corsHeaderOverrideFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain)
                    throws ServletException, IOException {

                // 获取请求的Origin
                String origin = request.getHeader("Origin");
                if(!EnvironmentUtil.isProd() ){
                    // 如果是开发环境，允许所有源
                    response.setHeader("Access-Control-Allow-Origin", "*");
                }else{
                    // 如果是生产环境，只允许指定源 Nginx 处理
                    response.setHeader("Access-Control-Allow-Origin", origin);
                }

                response.setHeader("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, PATCH");
                response.setHeader("Access-Control-Allow-Headers",
                        "Authorization, Content-Type, X-Requested-With, Accept, Origin, "
                                + "X-Terminal-Type, Terminal-Type");
                response.setHeader("Access-Control-Expose-Headers",
                        "Authorization, Content-Disposition, X-Terminal-Type");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Vary", "Origin");

                // 如果是OPTIONS预检请求，直接返回200
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    return; // 不继续过滤器链
                }

                // 对于非 OPTIONS 请求，继续过滤器链
                filterChain.doFilter(request, response);
            }
        };
    }
}
