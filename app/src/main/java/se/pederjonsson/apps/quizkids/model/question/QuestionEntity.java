package se.pederjonsson.apps.quizkids.model.question;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import se.pederjonsson.apps.quizkids.MainActivity;

@Entity(tableName = MainActivity.TABLE_NAME_QUESTION)
public class QuestionEntity implements Serializable {

    @PrimaryKey
    private int arrayid;

    @ColumnInfo(name = "drawableid")
    private int drawableid;

    @ColumnInfo(name = "questiontext")
    private String questiontext;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "trueanswer")
    private String trueanswer;

    @ColumnInfo(name = "anwer2")
    private String answer2;

    @ColumnInfo(name = "anwer3")
    private String answer3;

    public QuestionEntity(int arrayid, String questiontext, int drawableid, String category, String trueanswer, String answer2, String answer3) {
        this.arrayid = arrayid;
        this.category = category;
        this.questiontext = questiontext;
        this.drawableid = drawableid;
        this.trueanswer = trueanswer;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public int getArrayid() {
        return arrayid;
    }

    public void setArrayid(int _qid) {
        this.arrayid = _qid;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public void setQuestiontext(String _questiontext) {
        this.questiontext = _questiontext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionEntity)) return false;

        QuestionEntity question = (QuestionEntity) o;

        if (arrayid != question.arrayid) return false;
        return questiontext != null ? questiontext.equals(question.questiontext) : question.questiontext == null;
    }



    @Override
    public int hashCode() {
        int result = arrayid;
        result = 31 * result + (questiontext != null ? questiontext.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "arrayid=" + arrayid +
                ", questiontext ='" + questiontext + '\'' +
                ", trueanswer ='" + trueanswer + '\'' +
                ", answer2 ='" + answer2 + '\'' +
                ", answer3 ='" + answer3 + '\'' +
                ", category ='" + category + '\'' +
                ", drawable_resid ='" + drawableid + '\'' +
                '}';
    }

    public int getDrawableid() {
        return drawableid;
    }

    public void setDrawableid(int drawableid) {
        this.drawableid = drawableid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTrueanswer() {
        return trueanswer;
    }

    public void setTrueanswer(String trueanswer) {
        this.trueanswer = trueanswer;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

}
