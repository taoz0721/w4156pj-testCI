package com.insomnia_studio.w4156pj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest()
@AutoConfigureMockMvc
class W4156ApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClientEntityRepository clientEntityRepository;

	@Autowired
	private ClientService clientService;


	String testClientName = "testClient";
	UUID testClientId = UUID.fromString("2235566f-a37f-4b5f-a0d9-6961689c46c1");
	UUID fakeClientId = UUID.fromString("2235566f-a37f-4b5f-a0d9-6961689c46c2");
	UUID testUserId = UUID.fromString("5911dcbb-4af7-4fcf-ba3c-62af503d4dc1");
	UUID testPostId = UUID.fromString("440fa679-93fa-4c27-aa7f-b76c02988c65");


	@Test
	void testClientRegister() throws Exception {
		Client client = new Client(testClientName);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/client/register")
						.content(new ObjectMapper().writeValueAsString(client))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.clientName").value(testClientName));
	}

	@Test
	void testAddUserValidClient() throws Exception {
		User user = new User(testClientId, "test", "user");

		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/user")
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("user"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testAddUserInvalidClient() throws Exception {
		User user = new User(fakeClientId, "test", "user");

		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/user")
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void testGetUserValidUser() throws Exception {
		User user = new User(testClientId, "test", "user");

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/user/".concat(testUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("user"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdateUserValidUser() throws Exception {
		User user = new User(testClientId, "test1", "user1");

		mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/user/".concat(testUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("test1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("user1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testAddPostValidClientValidUser() throws Exception {
		Post post = new Post(testClientId, testUserId, "testPost", "testPost");

		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/post")
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("testPost"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testPost"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetPostValidClientValidUser() throws Exception {
		Post post = new Post(testClientId, testUserId, "testPost", "testPost");

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/post/".concat(testPostId.toString()))
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("testPost"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testPost"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdatePostValidClientValidUser() throws Exception {
		Post post = new Post(testClientId, testUserId, "testPost1", "testPost1");

		mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/post/".concat(testPostId.toString()))
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("testPost1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testPost1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testDeletePostValidClientValidUser() throws Exception {
		Post post = new Post(testClientId, testUserId, "testPost2", "testPost2");

		// Add a new post to be deleted
		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/post/")
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("testPost2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testPost2"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		UUID postIdDelete = UUID.fromString("dbafb5ef-dcd2-4baa-9ee4-51ef00798d36");
		// Delete the post
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/api/v1/post/".concat(postIdDelete.toString()))
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}








}


