package se.kth.jpf_visual;



public class Topic {

    private String content;

    public Topic(){
        this("");
    }

    public Topic(String content){
        setContent(content);
    }

    public String toString(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

}
