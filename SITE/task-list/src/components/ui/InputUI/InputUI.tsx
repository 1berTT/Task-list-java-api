"use client";

import { forwardRef, useState } from "react";
import { Eye, EyeOff } from "lucide-react";
import {
  Wrapper,
  Label,
  InputWrapper,
  IconContainer,
  Field,
  ErrorText,
  ToggleButton,
} from "./styles";
import { InputProps } from "./types";

export const InputUI = forwardRef<HTMLInputElement, InputProps>(
  ({ label, error, icon, mask, type, onChange = () => {}, ...rest }, ref) => {
    const [showPassword, setShowPassword] = useState(false);
    const isPassword = type === "password";
    const inputType = isPassword && showPassword ? "text" : type ?? "text";

    function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
      let value = e.target.value;

      if (mask) {
        value = mask(value);
        e.target.value = value;
      }

      onChange(e);
    }

    return (
      <Wrapper>
        {label && <Label>{label}</Label>}

        <InputWrapper $hasError={!!error}>
          {icon && <IconContainer>{icon}</IconContainer>}

          <Field ref={ref} type={inputType} onChange={handleChange} {...rest} />

          {isPassword && (
            <ToggleButton
              type="button"
              onClick={() => setShowPassword((prev) => !prev)}
              aria-label={showPassword ? "Ocultar senha" : "Mostrar senha"}
            >
              {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
            </ToggleButton>
          )}
        </InputWrapper>

        {error && <ErrorText>{error}</ErrorText>}
      </Wrapper>
    );
  },
);

InputUI.displayName = "InputUI";
