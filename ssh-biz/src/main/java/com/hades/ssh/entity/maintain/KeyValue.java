package com.hades.ssh.entity.maintain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.hades.ssh.common.entity.BaseEntity;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:19:13
 * <p>Version: 1.0
 */
@Entity
@Table(name = "maintain_map")
public class KeyValue extends BaseEntity<Long> {

	private static final long serialVersionUID = -6090376513587468282L;

	@Length(min = 1, max = 200, message = "{keyvalue.key.not.valid}")
    @Column(name = "map_key")
    private String key;

    @Length(min = 1, max = 500, message = "{keyvalue.value.not.valid}")
    @Column(name = "map_value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
