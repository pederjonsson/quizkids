package se.pederjonsson.apps.quizkids.components.room;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import se.pederjonsson.apps.quizkids.Objects.Question;

@Entity
public class QuestionEntity {

    @PrimaryKey(autoGenerate = true)
    private int qid;

    @ColumnInfo(name = "drawableid")
    private int drawable_resid;

    private String questiontext;

    //private Question.DifficultyLevel difficultyLevel;

    @ColumnInfo(name = "category")
    private Question.Category category;

    public QuestionEntity(int _qid, String _questionstring, int _drawable_resid, Question.Category _category) {
        this.qid = _qid;
        category = _category;
        this.questiontext = _questionstring;
        this.drawable_resid = _drawable_resid;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int _qid) {
        this.qid = _qid;
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

        if (qid != question.qid) return false;
        return questiontext != null ? questiontext.equals(question.questiontext) : question.questiontext == null;
    }



    @Override
    public int hashCode() {
        int result = qid;
        result = 31 * result + (questiontext != null ? questiontext.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "qid=" + qid +
                ", questiontext='" + questiontext + '\'' +
                ", drawable_resid='" + drawable_resid + '\'' +
                '}';
    }

    public int getDrawable_resid() {
        return drawable_resid;
    }

    public void setDrawable_resid(int drawable_resid) {
        this.drawable_resid = drawable_resid;
    }

    public Question.Category getCategory() {
        return category;
    }

    public void setCategory(Question.Category category) {
        this.category = category;
    }

}
