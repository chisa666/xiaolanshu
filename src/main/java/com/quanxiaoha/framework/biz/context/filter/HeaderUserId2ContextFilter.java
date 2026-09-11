package com.quanxiaoha.framework.biz.context.filter;

import com.quanxiaoha.framework.biz.context.holder.LoginUserContextHolder;
import com.quanxiaoha.framework.common.constant.GlobalConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Copies the gateway user id header into the shared request context. */
public class HeaderUserId2ContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String userId = request.getHeader(GlobalConstants.USER_ID);
        if (StringUtils.isBlank(userId)) {
            chain.doFilter(request, response);
            return;
        }
        LoginUserContextHolder.setUserId(userId);
        try {
            chain.doFilter(request, response);
        } finally {
            LoginUserContextHolder.remove();
        }
    }
}