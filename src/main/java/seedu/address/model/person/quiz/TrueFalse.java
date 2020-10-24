package seedu.address.model.person.quiz;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.person.exceptions.InvalidQuestionAttemptException;

/**
 * Represents a True/False question. This question has only two valid options: true and false.
 */
public class TrueFalse extends Question {

    private boolean answer;

    /**
     * Every field must be present and not null.
     */
    public TrueFalse(String prompt, boolean answer) {
        super(prompt);
        requireNonNull(answer);
        this.answer = answer;
    }

    public boolean getAnswer() {
        return answer;
    }

    /**
     * Returns the question prompt and options, represented in an appropriate String format.
     */
    public String getQuestion() {
        String question = "";
        question += prompt + " ";
        question += "True or False?";
        return question;
    }

    /**
     * Checks whether the attempt is a valid option.
     */
    public boolean isValidResponse(String attempt) {
        String formattedAttempt = attempt.toLowerCase();
        return (formattedAttempt.equals("true")) || (formattedAttempt.equals("false"));
    }

    /**
     * Checks whether the response is correct.
     * @param response user response
     * @return true if the response is correct and false otherwise
     * @throws InvalidQuestionAttemptException if the response is not valid
     */
    public boolean checkResponse(String response) throws InvalidQuestionAttemptException {
        if (!isValidResponse(response)) {
            throw new InvalidQuestionAttemptException("Response is not a valid option.");
        }
        String formattedResponse = response.toLowerCase();
        if (formattedResponse.equals("true")) {
            return answer;
        } else {
            return !answer;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof TrueFalse) {
            TrueFalse trueFalse = (TrueFalse) (other);
            return trueFalse.prompt.equals(prompt) && (trueFalse.answer == answer);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prompt, answer);
    }

    @Override
    public String toString() {
        return getQuestion();
    }
}