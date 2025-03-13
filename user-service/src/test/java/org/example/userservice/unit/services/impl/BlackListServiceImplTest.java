package org.example.userservice.unit.services.impl;

import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.impl.BlackListServiceImpl;
import org.example.userservice.utiles.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlackListServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private BlackListServiceImpl blackListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToBlackList_success() {
        Long requesterId = 1L;
        Long targetId = 2L;

        User requester = new User();
        requester.setId(requesterId);
        requester.setUserBlackList(new HashSet<>());

        when(userRepository.findUserWithLock(requesterId))
                .thenReturn(Optional.of(requester));
        when(userRepository.existsById(targetId))
                .thenReturn(true);

        blackListService.addToBlackList(requesterId, targetId);

        assertTrue(requester.getUserBlackList().contains(targetId));
        verify(userRepository).save(requester);
    }

    @Test
    void addToBlackList_userAlreadyBlacklisted_throwsException() {
        Long requesterId = 1L;
        Long targetId = 2L;

        User requester = new User();
        requester.setId(requesterId);
        requester.setUserBlackList(new HashSet<>(Set.of(targetId)));

        when(userRepository.findUserWithLock(requesterId)).
                thenReturn(Optional.of(requester));
        when(userRepository.existsById(targetId)).
                thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                blackListService.addToBlackList(requesterId, targetId));
        verify(userRepository, never()).save(any());
    }

    @Test
    void removeFromBlackList_success() {
        Long requesterId = 1L;
        Long targetId = 2L;

        User requester = new User();
        requester.setId(requesterId);
        requester.setUserBlackList(new HashSet<>(Set.of(targetId)));

        when(userRepository.findUserWithLock(requesterId))
                .thenReturn(Optional.of(requester));
        when(userRepository.existsById(targetId))
                .thenReturn(true);

        blackListService.removeFromBlackList(requesterId, targetId);

        assertFalse(requester.getUserBlackList().contains(targetId));
        verify(userRepository).save(requester);
    }

    @Test
    void removeFromBlackList_userNotInBlackList_throwsException() {
        Long requesterId = 1L;
        Long targetId = 2L;

        User requester = new User();
        requester.setId(requesterId);
        requester.setUserBlackList(new HashSet<>());

        when(userRepository.findUserWithLock(requesterId))
                .thenReturn(Optional.of(requester));
        when(userRepository.existsById(targetId))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> blackListService.removeFromBlackList(requesterId, targetId)
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    void getBlackList_success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUserBlackList(new HashSet<>(Set.of(2L, 3L)));

        User blackListedUser = new User();
        blackListedUser.setId(2L);
        blackListedUser.setUsername("user2");

        LiteUserDto dto = LiteUserDto.builder()
                .id(2L)
                .username("user2")
                .avatarUrl("")
                .build();
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        Page<User> page = new PageImpl<>(
                List.of(blackListedUser),
                PageRequest.of(
                        0,
                        10,
                        Sort.by("username").ascending()
                ),
                1
        );
        when(userRepository.findBlackListedUsers(anySet(), any()))
                .thenReturn(page);
        when(mapper.convertToDto(blackListedUser))
                .thenReturn(dto);

        Page<LiteUserDto> result = blackListService.getBlackList(userId, 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("user2", result.getContent().get(0).getUsername());
        verify(mapper).convertToDto(blackListedUser);
    }


    @Test
    void getBlackList_emptyBlackList_returnsEmptyPage() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUserBlackList(new HashSet<>());

        User blackListedUser = new User();
        blackListedUser.setId(2L);
        blackListedUser.setUsername("user2");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        Page<LiteUserDto> result = blackListService.getBlackList(userId, 0, 10);

        assertTrue(result.isEmpty());
        verify(userRepository, never()).findBlackListedUsers(anySet(), any());
    }
}
