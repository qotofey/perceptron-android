package ru.qotofey.perceptron.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.qotofey.perceptron.net.rest.model.response.Answer;
import ru.qotofey.perceptron.net.rest.model.response.Question;

public class SampleStorage {

    private List<Sample> mSampleList;

    public SampleStorage(List<Sample> sampleList) {
        mSampleList = sampleList;
    }

    public SampleStorage() {
        mSampleList = new ArrayList<>();
    }

    public SampleStorage insertAnswerList(List<Answer> answerList) {
        for (Answer answer : answerList) {
            boolean isExist = false;
            for (Sample sample : mSampleList) {
                if (sample.getAnswer().id.equals(answer.id)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) mSampleList.add(new Sample(answer));
        }
        return this;
    }

    public SampleStorage insertQuestionList(List<Question> questionList) {
        for (Sample sample : mSampleList) {
            sample.distribute(questionList);
        }
        return this;
    }

    public List<Sample> getSampleList() {
        return mSampleList;
    }

    public void setSampleList(List<Sample> sampleList) {
        mSampleList = sampleList;
    }

    public Observer<List<Answer>> answerListObserver = new Observer<List<Answer>>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Answer> answerList) {
            SampleStorage.this.insertAnswerList(answerList);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    };

    public Observer<List<Question>> questionListObserver = new Observer<List<Question>>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Question> questionList) {
            SampleStorage.this.insertQuestionList(questionList);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    };

}
