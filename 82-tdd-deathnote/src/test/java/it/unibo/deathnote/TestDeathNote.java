package it.unibo.deathnote;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.Executable.*;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

class TestDeathNote {
    
    List<String> RULES = List.of(
        """
        The human whose name is written in this note shall die.
        """,
        """
        This note will not take effect unless the writer has the subject's face in mind when
        writing his/her name. This is to prevent people who share the same name from being
        affected.
        """,
        """
        After writing the cause of death, details of the death should be written in the next 6
        seconds and 40 milliseconds.
        """,
        """
        The human who touches the Death Note can recognize the image and voice of its original
        owner, a god of death, even if the human is not the owner of the note.
        """,
        """
        The person in possession of the Death Note is possessed by a god of death,
        its original owner, until they die.
        """,
        """
        Gods of death, the original owners of the Death Note, do not do, in principle,
        anything which will help or prevent the deaths in the note. A god of death has no
        obligation to completely explain how to use the note or rules which will apply to the
        human who owns it unless asked.
        """,
        """
        A god of death can extend their own life by putting a name on their own note, but
        humans cannot.
        """,
        """
        The human who becomes the owner of the Death Note can, in exchange of half his/her
        remaining life, get the eyeballs of the god of death which will enable him/her to see
        a human's name and remaining life span when looking through them.
        """,
        """
        The conditions for death will not be realized unless it is physically possible for
        that human or it is reasonably assumed to be carried out by that human.
        """,
        """
        One page taken from the Death Note, or even a fragment of the page, contains the full
        effects of the note.
        """,
        """
        The individuals who lose the ownership of the Death Note will also lose their memory
        of the usage of the Death Note. This does not mean that he will lose all the memory
        from the day he owned it to the day he loses possession, but means he will only lose
        the memory involving the Death Note.
        """,
        """
        The number of pages of the Death Note will never run out.
        """,
        """
        It is useless trying to erase names written in the Death Note with erasers or
        white-out.
        """
    );

    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        this.deathNote = new DeathNoteImplementation();
    }

    @Test
    void assertGettingRules() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathNote.getRule(0);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathNote.getRule(1000);
            }
        });
        for(int i = 0 ; i < RULES.size() ; i++ ) {
            assertEquals(RULES.get(i), deathNote.getRule(i + 1));
        }
    }

    @Test
    void assertNames() {
        final String rossi = "Rossi";
        assertFalse(deathNote.isNameWritten(rossi));
        deathNote.writeName(rossi);
        assertTrue(deathNote.isNameWritten(rossi));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void assertCauseDeath() {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathNote.writeDeathCause("Stabbed");
            }
        });
        final String bianchi = "Bianchi";
        deathNote.writeName(bianchi);
        assertEquals("Heart attack", deathNote.getDeathCause(bianchi));
        final String verdi = "Verdi";
        deathNote.writeName(verdi);
        deathNote.writeDeathCause("karting accident");
        assertEquals("karting accident", deathNote.getDeathCause(verdi));
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(deathNote.writeDeathCause("Suicide"));
        assertEquals("karting accident", deathNote.getDeathCause(verdi));
    }

    @Test
    void assertDetails() {
        
    }
}