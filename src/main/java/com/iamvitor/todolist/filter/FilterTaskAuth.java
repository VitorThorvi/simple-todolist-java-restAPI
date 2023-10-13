package com.iamvitor.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.iamvitor.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var servletPath = request.getServletPath();
    if (servletPath.equals("/tasks/")) {
      //    auth
      var authorization = request.getHeader("Authorization");
      var trimmedAuthEncoded = authorization.substring("Basic".length()).trim();

      byte[] decodedAuth = Base64.getDecoder().decode(trimmedAuthEncoded);

      var authString = new String(decodedAuth);
      String[] credentials = authString.split(":");
      String username = credentials[0];
      String password = credentials[1];

/*    System.out.println("Authorization");
    System.out.println(trimmedAuthEncoded);
    System.out.println(username);
    System.out.println(password);
    */

//    user validation

      var user = this.userRepository.findByUsername(username);
      if (user == null) {
        response.sendError(401);
      } else {
        //    password validation
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (passwordVerify.verified) {
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401);
        }
      }
    } else {
      filterChain.doFilter(request, response);
    }


  }
}