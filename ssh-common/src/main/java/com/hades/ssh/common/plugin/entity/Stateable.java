package com.hades.ssh.common.plugin.entity;

/**
 * <p>实体实现该接口，表示需要进行状态管理
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午2:44:18
 * <p>Version: 1.0
 */
public interface Stateable<T extends Enum<? extends Stateable.Status>> {

    public void setStatus(T status);

    public T getStatus();


    /**
     * 标识接口，所有状态实现，需要实现该状态接口
     */
    public static interface Status {
    }

    /**
     * 审核状态
     */
    public static enum AuditStatus implements Status {
        waiting("等待审核"), fail("审核失败"), success("审核成功");
        private final String info;

        private AuditStatus(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 是否显示
     */
    public static enum ShowStatus implements Status {
        hide("不显示"), show("显示");
        private final String info;

        private ShowStatus(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }
}
