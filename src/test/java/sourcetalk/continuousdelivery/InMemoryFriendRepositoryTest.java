package sourcetalk.continuousdelivery;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InMemoryFriendRepositoryTest {
    @Test
    public void addFriend() {
        InMemoryFriendRepository repository = new InMemoryFriendRepository();
        Friend friend = new Friend("Inspector", "Gadget");
        repository.addFriend(friend);
        Friend friend1 = new Friend("Inspector", "Gadget");
        repository.addFriend(friend1);
        assertTrue(repository.getFriends().contains(friend));
        assertTrue(repository.getFriends().contains(friend1));
    }
}
