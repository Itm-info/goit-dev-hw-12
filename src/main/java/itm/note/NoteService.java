package itm.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class NoteService {
    final ConcurrentMap<Long, Note> notes;

    public List<Note> listAll() {
        final List<Note> res = null;
        notes.forEach((l,n) -> res.add(n));
        return res;
    }

    public Note add(Note note) {
        Random random = new Random();
        long id;

        do {
            id = random.nextLong();
            note.setId(id);
        }
        while (! notes.putIfAbsent(id,note).equals(note) );

        return note;
    }

    void deleteById(long id) throws NullPointerException {
        notes.remove(id);
    }

    void update(Note note) throws NullPointerException {
        notes.computeIfPresent( note.getId(), (i,n) -> note );
    }

    Note getById(long id) throws NullPointerException {
        return notes.get(id);
    }
}
