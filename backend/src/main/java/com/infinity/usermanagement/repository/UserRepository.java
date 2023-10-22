package com.infinity.usermanagement.repository;

import com.infinity.usermanagement.model.document.User;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.Optional;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "user", viewName = "all")
public interface UserRepository extends CouchbaseRepository<User, String> {
    Optional<User> findByEmail(String email);
}