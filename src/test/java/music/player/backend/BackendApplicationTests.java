package music.player.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import music.player.backend.model.Person;
import music.player.backend.model.Playlist;
import music.player.backend.repository.PersonRepository;
import music.player.backend.repository.PlaylistRepository;
import music.player.backend.repository.SongRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private PlaylistRepository playlistRepository;
	@Autowired
	private SongRepository songRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void addPersonTest() throws Exception {
		mockMvc.perform(post("/person/add?username=testuser&password=testpass"))//utworzenie testowego person
			.andExpect(status().isOk())//sprawdzenie czy ok
			.andExpect(jsonPath("$.username").value("testuser"));//czy zwrotny json ma pole username o nicku testuser
		var person = personRepository.findByUsername("testuser");
		if (person != null) personRepository.deleteById(person.getId());//usuwanie z bazy testu
	}

	@Test
	void addSongTest() throws Exception {
		mockMvc.perform(post("/songs/add")//endpoint dodajacy piosenke
			.contentType(MediaType.APPLICATION_JSON)//typ=json
			.content("{\"id\":\"100\",\"title\":\"Test Song\",\"artist\":\"Test Artist\",\"url\":\"http://test.url\"}"))//dane piosenki
			.andExpect(status().isOk())//czy ok
			.andExpect(jsonPath("$.title").value("Test Song"));//czy zwraca tytul Test Song
		songRepository.deleteById("100");//usuwa piosenke z backendu
	}

	@Test
	void addPlaylistTest() throws Exception {
		String personJson = mockMvc.perform(post("/person/add?username=playlistuser&password=pass"))//testowy uzytkownik
			.andExpect(status().isOk())//czy ok
			.andReturn().getResponse().getContentAsString();
		Gson gson = new Gson();
		Person person = gson.fromJson(personJson, Person.class);//json->Person
		String personId = person.getId();

		String playlistJson = mockMvc.perform(post("/playlist/create?name=TestPlaylist&ownerId=" + personId))//testowa playlista
			.andExpect(status().isOk())//czy ok
			.andExpect(jsonPath("$.name").value("TestPlaylist"))//czy zwraca playliste o nazwie TestPlaylist
			.andReturn().getResponse().getContentAsString();
		Playlist playlist = gson.fromJson(playlistJson, Playlist.class);//json->Playlist
		String playlistId = playlist.getId();
		playlistRepository.deleteById(playlistId);//usuwanie playlisty
		personRepository.deleteById(personId);//usuwanie person
	}


	@Test
	void getSongsTest() throws Exception {
		mockMvc.perform(get("/songs"))//pobieranie wszystkich piosenek
			.andExpect(status().isOk());//czy ok
	}

	@Test
	void removeSongTest() throws Exception {
		mockMvc.perform(post("/songs/add")//dodanie piosenki testowej
			.contentType(MediaType.APPLICATION_JSON)//typ=json
			.content("{\"id\":\"200\",\"title\":\"Remove Song\",\"artist\":\"Test\",\"url\":\"http://test.url\"}"))//zawartosc piosenki
			.andExpect(status().isOk());//czy ok
		mockMvc.perform(delete("/songs/remove/200"))//usuwanie piosenki
			.andExpect(status().isOk());//czy ok
	}

	@Test
	void loginPersonTest() throws Exception {
		mockMvc.perform(post("/person/add?username=logintest&password=pass"))//tworzenie person
			.andExpect(status().isOk());//czy ok
		var loginResult = mockMvc.perform(get("/person/login?username=logintest&password=pass"))//logowanie
			.andExpect(status().isOk())//czy ok
			.andReturn().getResponse().getContentAsString();
		// loginResult to id użytkownika jako plain text
		personRepository.deleteById(loginResult.trim());//usuwanie uzytkownika
	}

	@Test
	void getPersonPlaylistsTest() throws Exception {
		String personJson = mockMvc.perform(post("/person/add?username=playlistlistuser&password=pass"))//tworzenie person
			.andExpect(status().isOk())//czy ok
			.andReturn().getResponse().getContentAsString();
		Gson gson = new Gson();
		Person person = gson.fromJson(personJson, Person.class);//json->Person
		String personId = person.getId();
		String playlistJson = mockMvc.perform(post("/playlist/create?name=UserPlaylist&ownerId=" + personId))//tworzenie playlisty
			.andExpect(status().isOk())//czy ok
			.andReturn().getResponse().getContentAsString();
		Playlist playlist = gson.fromJson(playlistJson, Playlist.class);//json->playlist
		String playlistId = playlist.getId();
		mockMvc.perform(get("/person/" + personId + "/playlists"))//pobieranie playlist z bazy
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value("UserPlaylist"));//czy nazwa pierwszej playlisty to UserPlaylist
		playlistRepository.deleteById(playlistId);//usuwanie playlisty
		personRepository.deleteById(personId);//usuwanie uzytkownika
	}
}
