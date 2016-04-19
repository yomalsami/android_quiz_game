/*
 * Author: Alex Ball
 * Date: 04/19/2016
 * Filename: QuizModel.java
 *
 * This class represents a quiz game in which a user has a score. They increase their score
 * by correctly answering true/false questions.
 */
package net.greenrivertech.alexb.quizgame;

import java.util.ArrayList;

/**
 * This class represents a quiz game in which a user has a score. They increase their score
 * by correctly answering true/false questions.
 *
 * @author Alex Ball
 */
public class QuizModel {
    /**
     * Default number of questions per quiz.
     */
    public static final int DEFAULT_NUM_QUESTIONS = 3;

    //the user's score; the number of correctly answered questions for this game.
    private int score;

    //array representing the list of questions used for this game.
    private Question[] questions;

    //the question number that the user is on (used for ordering the questions; user may be
    //on 1st question (0), or 3rd question (2),etc.)
    private int gameQuestionNum;

    //The number of questions that the user has answered in this game.
    private int numQuestionsAnswered;

    //The number of questions in this quiz
    private int numQuestions;

    /**
     * The full list of questions that the quiz game might ask the user.
     * A small number of questions are selected from this list for each game.
     */
    public static ArrayList<Question> fullQuestionList;

    static {
        fullQuestionList = new ArrayList<>();
        fullQuestionList.add(new Question("Question 1: answer is true", true));
        fullQuestionList.add(new Question("Question 2: answer is false", false));
        fullQuestionList.add(new Question("Question 3: answer is true", true));
    }

    /**
     * Constructor that creates a new QuizModel with score 0 and gameQuestionNum 0 (first question).
     */
    public QuizModel() {
        numQuestions = DEFAULT_NUM_QUESTIONS;
        score = 0;
        gameQuestionNum = 0;
        numQuestionsAnswered = 0;

        //fill the array with questions.
        questions = new Question[numQuestions];
        for(int i = 0; i < numQuestions; i++) {
            questions[i] = fullQuestionList.get(i);
        }
    }

    /**
     * Returns the current score (number of correctly answered questions).
     *
     * @return Current score.
     */
    public int getScore() {
        return score;
    }

    public int getNumQuestionsAnswered() {
        return numQuestionsAnswered;
    }

    /**
     * Returns the current question.
     *
     * @return The current question
     */
    public String getCurrentQuestion() {
        return questions[gameQuestionNum].getQuestion();
    }

    /**
     * Takes the user's answer and compares it to the correct answer. If they match, then
     * the user's score increments and this method returns true. Otherwise, this method
     * returns false. Either way, move on to next question and increment numQuestionsAnswered.
     *
     * @param answer The user's answer.
     *
     * @throws IllegalStateException Throws IllegalStateException if called when
     * the game is already over.
     *
     * @return True if the user was correct, false otherwise.
     */
    public boolean answerQuestion(boolean answer) {

        //if the game is over, then a question cannot be answered.
        if (isGameOver()) {
            throw new IllegalStateException("Cannot answer questions when game is over.");
        }

        //assume user was incorrect.
        boolean result = false;

        //if the answer to the question represented by currentQuestionNum matches
        //the user's answer, then increase score and set result to true (user was correct).
        if (answer == questions[gameQuestionNum].getCorrectAnswer()) {
            result = true;
            score++;
        }

        //the user answered a question; increment numQuestionsAnswered.
        numQuestionsAnswered++;

        //advance to the next question.
        nextQuestion();

        //return result (true if user was correct, false otherwise).
        return result;
    }

    /**
     * Advance to the next question without answering the current one.
     * Returns true if successful, false otherwise.
     *
     * @return True if successful, false otherwise.
     */
    public boolean nextQuestion() {
        //if this is the last question, do not advance, and return false.
        if (gameQuestionNum >= numQuestions - 1) {
            return false;
        }

        //if this is not the last question, advance to the next question and return true.
        gameQuestionNum++;
        return true;
    }

    /**
     * Go back to the previous question without answering the current one.
     * Returns true if successful, false otherwise.
     *
     * @return True if successful, false otherwise.
     */
    public boolean previousQuestion() {
        //if this is the first question, do not go back, and return false.
        if (gameQuestionNum <= 0) {
            return false;
        }

        //if this is not the first question, go back to the previous question and return true.
        gameQuestionNum--;
        return true;
    }

    /**
     * Returns true if the game is over. The game is considered over when the user has
     * answered numQuestions questions (whether they are correct or not).
     *
     * @return True if the user has answered enough questions, false otherwise.
     */
    public boolean isGameOver() {
        return numQuestionsAnswered >= numQuestions;
    }
}
