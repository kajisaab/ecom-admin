package com.kajisaab.ecommerce.feature.auth.repository;

import com.kajisaab.ecommerce.feature.auth.entity.OtpSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpSettingRepository extends JpaRepository<OtpSetting, String> {
}
