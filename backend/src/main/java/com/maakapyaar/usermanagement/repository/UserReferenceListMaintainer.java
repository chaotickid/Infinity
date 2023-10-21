package com.maakapyaar.usermanagement.repository;

import com.maakapyaar.couchbaseRefereceListMaintainer.UserReferenceList;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "userReferenceListMaintainer", viewName = "all")
public interface UserReferenceListMaintainer extends CouchbaseRepository<UserReferenceList, String> {
}