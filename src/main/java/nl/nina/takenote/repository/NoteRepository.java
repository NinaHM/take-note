package nl.nina.takenote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.nina.takenote.model.Note;

public interface NoteRepository extends JpaRepository<Note, Integer> {

}
