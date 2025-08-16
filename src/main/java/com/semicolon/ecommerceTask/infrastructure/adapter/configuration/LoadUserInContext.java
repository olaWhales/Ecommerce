//package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;
//
//import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Component
//@Slf4j
//
//public class LoadUserInContext extends OncePerRequestFilter {
//    private final UserPersistenceOutPort userPersistenceOutPort;
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Exit early if there's no user authenticated or it's an anonymous user
//        if (!(authentication instanceof JwtAuthenticationToken jwtAuthToken)) {
//            log.debug("No user logged in or not a JWT token.");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            Object emailClaim = jwtAuthToken.getToken().getClaim("email");
//
//            if (emailClaim != null) {
//                String email = (String) emailClaim;
//
//                UserDomainObject user = userPersistenceOutPort.findUserByEmail(email);
//
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        user,
//                        jwtAuthToken.getCredentials(),
//                        jwtAuthToken.getAuthorities()
//                );
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//
//                log.debug("User context set for: {}", email);
//            }
//        } catch (Exception e) {
//            log.error("Error loading user context: {}", e.getMessage());
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//}
