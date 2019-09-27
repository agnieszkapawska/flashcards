INSERT INTO USER (id, user_name, password) VALUES
(1, 'Kasia', 'pass'),
(2, 'Aga', 'zzz'),
(3, 'Marta', 'mmm'),
(4, 'Chris', 'hello');

INSERT INTO FLASHCARDS_TO_LEARN (id, user_id) VALUES
(1, 1),
(2, 2);

INSERT INTO FLASHCARDS_TO_REPEAT (id, user_id) VALUES
(1, 3);

INSERT INTO FLASHCARDS_TO_REFRESH (id, user_id) VALUES
(1, 4);

INSERT INTO FLASHCARD
(id, answer, correct_answer_counter, example_usage, explanation, question, user_id,
 flashcards_to_learn_id, flashcards_to_repeat_id, flashcards_to_refresh_id) VALUES
(1, 'home', 2, 'at home', 'place where you live', 'dom', 1, 1, null, null),
(2, 'cat', 2, 'happy cat', 'sweet animal', 'kot', 1, 1, null, null),
(3, 'air', 0, 'fell the air', 'you breathe because of it', 'powietrze', 2, 2, null, null),
(4, 'computer', 0, 'computer sickness', 'programmers working using it', 'komputer', 3, null, 1, null),
(5, 'carrot', 0, 'good carrot', 'vegatable', 'marchewka', 4, null, 1, null);