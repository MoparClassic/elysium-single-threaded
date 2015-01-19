package org.moparscape.elysium.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by daniel on 18/01/2015.
 */
@Entity
@Table(name = "Customer")
public class Customer {

    private String accountOwnerName;
    private int id;

    @Column(name = "AccountOwnerName")
    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String name) {
        this.accountOwnerName = name;
    }

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
