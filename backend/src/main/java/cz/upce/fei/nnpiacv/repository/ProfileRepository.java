package cz.upce.fei.nnpiacv.repository;

import cz.upce.fei.nnpiacv.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
