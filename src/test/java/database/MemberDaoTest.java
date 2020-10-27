package database;

import no.kristiania.database.MemberDao;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDaoTest {

    @Test
    void shouldListInsertedMembers() {
        MemberDao memberDao = new MemberDao();
        String member = exampleMember();
        memberDao.insert(member);
        assertThat(memberDao.list()).contains(member);
    }

    private String exampleMember() {
        String[] options = {"Ben", "Arzana", "Magbule", "Osman", "Laurent"};
        Random random = new Random();
        return options[random.nextInt(options.length)];
    }

}
