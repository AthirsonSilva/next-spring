package com.api.nextspring.utils;

import com.api.nextspring.entity.GenreEntity;
import com.api.nextspring.entity.RoleEntity;
import com.api.nextspring.entity.UserEntity;
import com.api.nextspring.enums.ApplicationUserRoles;
import com.api.nextspring.repositories.GenreRepository;
import com.api.nextspring.repositories.RoleRepository;
import com.api.nextspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final GenreRepository genreRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		createApplicationRoles();
	}

	private void createApplicationRoles() {
		if (!checkIfAdminRoleAlreadyExists()) createAdminRole();
		if (!checkIfUserRoleAlreadyExists()) createUserRole();
		if (!checkIfAdminAlreadyExists()) createAdmin();
		if (!checkIfUserAlreadyExists()) createUser();
		if (!checkIfGenreAlreadyExists()) createGenres();

		System.out.println("\n------------ Application Bootstraped!!! ------------\n");
	}

	private void createUser() {
		userRepository.save(UserEntity
				.builder()
				.name("user")
				.cpf("00000000000")
				.email("user@user.com")
				.password(passwordEncoder.encode("user"))
				.roles(Set.of(
						roleRepository.findByName(ApplicationUserRoles.USER.name()).orElseThrow(
								() -> new RuntimeException("User role not found!"))
				))
				.build());

		System.out.println("\n------------ User created!!! ------------\n");
	}

	private void createAdmin() {
		userRepository.save(UserEntity
				.builder()
				.name("admin")
				.cpf("11111111111")
				.email("admin@admin.com")
				.password(passwordEncoder.encode("admin"))
				.roles(Set.of(
						roleRepository.findByName(ApplicationUserRoles.ADMIN.name()).orElseThrow(
								() -> new RuntimeException("Admin role not found!"))
				))
				.build());

		System.out.println("\n------------ Admin created!!! ------------\n");
	}

	private void createGenres() {
		genreRepository.save(GenreEntity
				.builder()
				.name("No Genre")
				.description("Games without genre")
				.build());
	}

	private boolean checkIfAdminAlreadyExists() {
		UserEntity admin = userRepository.findByEmail("admin@admin.com").orElse(null);

		return admin != null;
	}

	private boolean checkIfUserAlreadyExists() {
		UserEntity user = userRepository.findByEmail("user@user.com").orElse(null);

		return user != null;
	}

	private void createUserRole() {
		roleRepository.save(RoleEntity
				.builder()
				.name(ApplicationUserRoles.USER.name())
				.build());

		System.out.println("\n------------ User role created!!! ------------\n");
	}

	private void createAdminRole() {
		roleRepository.save(RoleEntity
				.builder()
				.name(ApplicationUserRoles.ADMIN.name())
				.build());

		System.out.println("\n------------ Admin role created!!! ------------\n");
	}

	private boolean checkIfAdminRoleAlreadyExists() {
		RoleEntity admin = roleRepository.findByName(ApplicationUserRoles.ADMIN.name()).orElse(null);

		return admin != null;
	}

	private boolean checkIfUserRoleAlreadyExists() {
		RoleEntity user = roleRepository.findByName(ApplicationUserRoles.USER.name()).orElse(null);

		return user != null;
	}

	private boolean checkIfGenreAlreadyExists() {
		GenreEntity genre = genreRepository.findByName("No Genre").orElse(null);

		return genre != null;
	}
}
