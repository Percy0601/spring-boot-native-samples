package io.samples.mybatis.vo;



/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
public class BookVO {
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BookVO{" + "id=" + id + ", title='" + title + '\'' + '}';
    }
}
