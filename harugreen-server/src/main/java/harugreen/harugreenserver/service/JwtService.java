package harugreen.harugreenserver.service;

import harugreen.harugreenserver.dto.user.JwtResponseDto;
import harugreen.harugreenserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {

    private ModelMapper mapper = new ModelMapper();
    private final UserRepository userRepository;




}
