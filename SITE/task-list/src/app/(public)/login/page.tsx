"use client";

import { InputUI } from "@/components/ui/InputUI/InputUI";
import { Container } from "./styles";
import { ButtonUI } from "@/components/ui/ButtonUI/ButtonUI";
import { useForm } from "react-hook-form";
import { loginFormSchema, LoginFormData } from "./formValidation";
import { zodResolver } from "@hookform/resolvers/zod";

export default function LoginPage() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginFormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  function onSubmit(data: LoginFormData) {
    console.log("Entrei aqui");
    console.log(data);
  }

  return (
    <Container onSubmit={handleSubmit(onSubmit)}>
      <h1>Login</h1>
      <InputUI
        label="Email"
        placeholder="Digite seu email"
        error={errors.email?.message}
        {...register("email")}
      />

      <InputUI
        label="Senha"
        placeholder="Digite sua senha"
        type="password"
        error={errors.password?.message}
        {...register("password")}
      />
      <ButtonUI variant="primary" type="submit" disabled={isSubmitting}>
        {isSubmitting ? "Entrando..." : "Entrar"}
      </ButtonUI>
    </Container>
  );
}
