"use client";

import Link from "next/link";
import { ChevronDown } from "lucide-react";
import { HeaderContainer, HeaderContent, HeaderRightSlot } from "./styles";
import { ButtonUI } from "@/components/ui/ButtonUI/ButtonUI";
import { useCurrentUser } from "@/hooks/useCurrentUser";

export default function Header() {
  const { user, isLoading, isAuthenticated } = useCurrentUser();

  return (
    <HeaderContainer>
      <HeaderContent>
        <h2>Task List</h2>

        <HeaderRightSlot>
          {!isAuthenticated ? (
            <Link href="/login">
              <ButtonUI variant="tertiary">Entrar</ButtonUI>
            </Link>
          ) : (
            <div className="user-info">
              <p>{isLoading ? "Carregando..." : (user?.name ?? "...")}</p>
              <ChevronDown size={20} />
            </div>
          )}
        </HeaderRightSlot>
      </HeaderContent>
    </HeaderContainer>
  );
}
