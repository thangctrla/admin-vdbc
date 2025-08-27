package vn.vdbc

import lombok.RequiredArgsConstructor
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@RequiredArgsConstructor
@EntityScan("vn.vdbc")
@EnableScheduling
class CommonAdminApplication {

    static void main(String[] args) {
        SpringApplication.run(CommonAdminApplication, args)
    }

}
