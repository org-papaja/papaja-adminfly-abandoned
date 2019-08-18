package org.papaja.adminfly.shared.service;

import org.papaja.adminfly.shared.vendor.hibernate.Pagination;
import org.papaja.adminfly.shared.entity.User;
import org.papaja.adminfly.shared.mapper.UserMapper;
import org.papaja.adminfly.shared.dto.UserDto;
import org.papaja.adminfly.shared.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class UserService {

    private static final int MAX_RESULT_PER_PAGE = 5;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roles;

    @Autowired
    private UserMapper mapper;

    public User loadUserByUsername(String username) {
        return repository.getUser(username);
    }

    public Pagination<User> getUsers(int offset) {
        return Pagination.of(repository.getUsersQuery(), offset, MAX_RESULT_PER_PAGE);
    }

    public List<User> getAllUsers() {
        return repository.getUsers();
    }

    public void merge(UserDto dto, User entity) {
        mapper.map(dto, entity);

        if (entity.isOld()) {
            entity.setRoles(roles.getRoles(dto.getRoles()));
        }

        merge(entity);
    }

    public void merge(User user) {
        repository.merge(user);
    }

    public User getUser(Integer id) {
        boolean isValid = (nonNull(id) && id > 0);

        return isValid ? repository.getUser(id) : new User();
    }

    public User getUser(Long id) {
        return getUser(id.intValue());
    }

}