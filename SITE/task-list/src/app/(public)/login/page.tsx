"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";
import { decodeJwtPayload, isAdminFromRoles } from "@/lib/auth";
import { InputUI } from "@/components/ui/InputUI/InputUI";
import { Container } from "./styles";
import { ButtonUI } from "@/components/ui/ButtonUI/ButtonUI";
import { useForm } from "react-hook-form";
import { loginFormSchema, LoginFormData } from "./formValidation";
import { zodResolver } from "@hookform/resolvers/zod";
import { api } from "@/services/api";
import { currentUserKeys } from "@/queries";

export default function LoginPage() {
  const router = useRouter();
  const queryClient = useQueryClient();
  const [authData, setAuthData] = useState<{
    token: string;
    expiresIn: number;
  } | null>(null);

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

  useEffect(() => {
    if (!authData) return;

    const saveTokenAndRedirect = async () => {
      const maxAgeSeconds = Math.floor(
        (authData.expiresIn - Date.now()) / 1000,
      );
      document.cookie = `token=${authData.token}; path=/; max-age=${maxAgeSeconds}`;
      localStorage.setItem("token", authData.token);

      const payload = decodeJwtPayload(authData.token);
      const id = payload?.sub;
      const isAdmin = isAdminFromRoles(payload?.roles ?? []);

      if (id) {
        try {
          const endpoint = isAdmin
            ? `/admin/load/${id}`
            : `/user/load/${id}`;
          const { data } = await api.get(endpoint);
          queryClient.setQueryData(
            currentUserKeys.detail(id, isAdmin),
            data,
          );
        } catch {
          // ignora erro; o Header vai refetch se necessário
        }
      }

      router.push("/dashboard");
    };

    saveTokenAndRedirect();
  }, [authData, router, queryClient]);

  async function onSubmit(data: LoginFormData) {
    try {
      const response = await api.post("/user/login", {
        email: data.email,
        password: data.password,
      });

      const { access_token, expires_in } = response.data;
      setAuthData({ token: access_token, expiresIn: expires_in });
    } catch (error) {
      console.error(error);
    }
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
