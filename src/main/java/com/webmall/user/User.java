package com.webmall.user;


import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.webmall.cart.CartItem;
import com.webmall.product.Product;
import com.webmall.store.Store;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Document("user")
public class User implements UserDetails{

    @Id
	private String id;

	@NotBlank(message =  "name cannot be blank")
	@NonNull
	private String firstName;

	@NotBlank(message =  "name cannot be blank")
	@NonNull
	private String lastName;

	@Indexed(unique = true)
	@NotBlank(message =  "email cannot be blank")
	@NonNull
	private String email;

	@NotBlank(message =  "password cannot be blank")
    @NonNull
	private String password;

	private UserRole role;

	private boolean emailConfirmed;

	private Address address;

	@DocumentReference
	private List<Store> storeIds;

	private List<CartItem> shoppingCart;

	private List<Product> wishlist;

	private List<Product> buyHistory;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean isEmailConfirmed() {
		return emailConfirmed;
	}
}
