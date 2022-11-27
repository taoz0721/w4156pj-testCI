package com.insomnia_studio.w4156pj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insomnia_studio.w4156pj.controller.CommentController;
import com.insomnia_studio.w4156pj.controller.PostController;
import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.service.ClientService;
import com.jayway.jsonpath.JsonPath;
import org.aspectj.weaver.bcel.FakeAnnotation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class W4156ApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClientEntityRepository clientEntityRepository;

	@Autowired
	private PostController postController;

	@Autowired
	private CommentController commentController;

	@Autowired
	private ClientService clientService;


	private String testClientName = "testClient";
//	UUID testClientId = UUID.fromString("2235566f-a37f-4b5f-a0d9-6961689c46c1");
	static UUID testClientId;
	UUID fakeClientId = UUID.fromString("2235566f-a37f-4b5f-a0d9-6961689c46c2");
//	UUID testUserId = UUID.fromString("5911dcbb-4af7-4fcf-ba3c-62af503d4dc1");
	static UUID testUserId;
	UUID fakeUserId = UUID.fromString("5911dcbb-4af7-4fcf-ba3c-62af503d4dc2");
//	UUID testPostId = UUID.fromString("440fa679-93fa-4c27-aa7f-b76c02988c65");
	static UUID testPostId;
	static UUID testCommentId;
	static UUID fakeCommentId = UUID.fromString("26878fd1-ad62-46ec-98cf-0b339c35d2ca");

	@Test
	@Order(1)
	void testClientRegister() throws Exception {
		Client client = new Client(this.testClientName);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/client/register")
						.content(new ObjectMapper().writeValueAsString(client))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.clientName").value(testClientName))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		testClientId = UUID.fromString(JsonPath.read(result.getResponse().getContentAsString(), "$.clientId"));
	}

	@Test
	@Order(2)
	void testAddUserValidClient() throws Exception {
		User user = new User(testClientId, "test", "user");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/user")
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("test"))
				.andExpect(jsonPath("$.lastName").value("user"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		testUserId = UUID.fromString(JsonPath.read(result.getResponse().getContentAsString(), "$.userId"));
	}



	@Test
	@Order(3)
	void testAddUserInvalidClient() throws Exception {
		User user = new User(this.fakeClientId, "test", "user");

		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/user")
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@Order(4)
	void testGetUserValidUser() throws Exception {
		User user = new User(testClientId);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/user/".concat(testUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("test"))
				.andExpect(jsonPath("$.lastName").value("user"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
//
	@Test
	@Order(5)
	void testGetUserInvalidUser() throws Exception {
		User user = new User(testClientId);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/user/".concat(fakeUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@Order(6)
	void testGetUserInvalidClient() throws Exception {
		User user = new User(fakeClientId);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/user/".concat(testUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@Order(7)
	void testUpdateUserValidUser() throws Exception {
		User user = new User(testClientId, "test3", "user3");

		// Add a new user to be deleted
		mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/user/".concat(testUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("test3"))
				.andExpect(jsonPath("$.lastName").value("user3"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(8)
	void testUpdateUserInvalidUser() throws Exception {
		User user = new User(testClientId, "test4", "user4");
//		UUID userIdUpdate = UUID.fromString(testUserId.toString());

		// Add a new user to be deleted
		mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/user/".concat(fakeUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@Order(9)
	void testUpdateUserInvalidClient() throws Exception {
		User user = new User(fakeClientId, "test4", "user4");
//		UUID userIdUpdate = UUID.fromString("012b3669-2fc7-4c35-8216-38aa68862129");

		// Add a new user to be deleted
		mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/user/".concat(testUserId.toString()))
						.content(new ObjectMapper().writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}


	@Test
	@Order(10)
	void testAddPostValidClientValidUser() throws Exception {
		Set<String> tags = new HashSet<>();
		tags.add("tag1");
		tags.add("tag2");
		Post post = new Post(testClientId, testUserId, "testPostTitle", "testPostContent", tags);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/post")
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("testPostTitle"))
				.andExpect(jsonPath("$.content").value("testPostContent"))
				.andExpect((jsonPath("$.tags", Matchers.containsInAnyOrder("tag1", "tag2"))))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		testPostId = UUID.fromString(JsonPath.read(result.getResponse().getContentAsString(), "$.postId"));
	}

	@Test
	@Order(11)
	void testGetPostValidClientValidUser() throws Exception {
		Post post = new Post(testClientId);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/post/".concat(testPostId.toString()))
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("testPostTitle"))
				.andExpect(jsonPath("$.content").value("testPostContent"))
				.andExpect((jsonPath("$.tags", Matchers.containsInAnyOrder("tag1", "tag2"))))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(12)
	void testUpdatePostValidClientValidUser() throws Exception {
		Set<String> tags = new HashSet<>();
		tags.add("tag3");
		tags.add("tag4");
		Post post = new Post(testClientId, testUserId, "testPost2", "testPost2", tags);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/post/".concat(testPostId.toString()))
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("testPost2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testPost2"))
				.andExpect((jsonPath("$.tags", Matchers.containsInAnyOrder("tag3", "tag4"))))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(13)
	void testDeletePostValidClientValidUser() throws Exception {
		Set<String> tags = new HashSet<>();
		tags.add("tag3");
		tags.add("tag4");
		Post post = new Post(testClientId, testUserId, "testPost2", "testPost2", tags);

		UUID postIdDelete = postController.addPost(post).getPostId();

		// Delete the post
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/api/v1/post/".concat(postIdDelete.toString()))
						.content(new ObjectMapper().writeValueAsString(post))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(14)
	void testAddCommentValidClientValidUser() throws Exception {
		Comment comment = new Comment(testClientId, testUserId, testPostId, 10, 2, "testComment");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/post/".concat(testPostId.toString()).concat("/comment"))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.postId").value(testPostId.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.likesNum").value(10))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dislikesNum").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testComment"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		testCommentId = UUID.fromString(JsonPath.read(result.getResponse().getContentAsString(), "$.commentId"));
	}

	@Test
	@Order(15)
	void testGetCommentValidClientValidUser() throws Exception {
		Comment comment = new Comment(testClientId, testUserId, testPostId, 10, 2, "testComment");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/comment/".concat(testCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.postId").value(testPostId.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.likesNum").value(10))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dislikesNum").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testComment"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}

	@Test
	@Order(16)
	void testGetCommentInValidClientValidUser() throws Exception {
		Comment comment = new Comment(fakeClientId, testUserId, testPostId, 10, 2, "testComment");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/comment/".concat(testCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andReturn();
	}

	@Test
	@Order(17)
	void testGetCommentInvalidClientInvalidComment() throws Exception {
		Comment comment = new Comment(testClientId, testUserId, testPostId, 10, 2, "testComment");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/comment/".concat(fakeCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();
	}

	@Test
	@Order(18)
	void testUpdateCommentValidClientValidComment() throws Exception {
		Comment comment = new Comment(testClientId, testUserId, testPostId, 10, 2, "testComment2");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/comment/".concat(testCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.postId").value(testPostId.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.likesNum").value(10))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dislikesNum").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("testComment2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}

	@Test
	@Order(19)
	void testUpdateCommentInvalidClientValidComment() throws Exception {
		Comment comment = new Comment(fakeClientId, testUserId, testPostId, 10, 2, "testComment2");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/comment/".concat(testCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andReturn();
	}

	@Test
	@Order(20)
	void testUpdateCommentValidClientInvalidComment() throws Exception {
		Comment comment = new Comment(testClientId, testUserId, testPostId, 10, 2, "testComment2");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.put("/api/v1/comment/".concat(fakeCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();
	}

	@Test
	@Order(21)
	void testDeleteCommentValidClientValidComment() throws Exception {
		Comment comment = new Comment(testClientId, testUserId, testPostId, 10, 2, "testComment3");

		UUID commentIdDelete = commentController.addComment(comment, testPostId).getCommentId();

		// Delete the comment
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/api/v1/comment/".concat(commentIdDelete.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(22)
	void testDeleteCommentInvalidClientValidComment() throws Exception {
		Comment comment = new Comment(fakeClientId, testUserId, testPostId, 10, 2, "testComment4");

		// Delete the comment
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/api/v1/comment/".concat(testCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@Order(23)
	void testDeleteCommentValidClientInvalidComment() throws Exception {
		Comment comment = new Comment(fakeClientId, testUserId, testPostId, 10, 2, "testComment5");

		// Delete the comment
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/api/v1/comment/".concat(fakeCommentId.toString()))
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}














}


