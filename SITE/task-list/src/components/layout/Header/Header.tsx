"use client";

import { ChevronDown } from "lucide-react";
import { HeaderContainer, HeaderContent, HeaderRightSlot } from "./styles";
import { ButtonUI } from "@/components/ui/ButtonUI/ButtonUI";

export default function Header() {
  return (
    <HeaderContainer>
      <HeaderContent>
        <h2>Task List</h2>

        <HeaderRightSlot>
          {/* <ButtonUI variant="tertiary">Entrar</ButtonUI> */}

          <div className="user-info">
            <p>Humberto Damasceno</p>
            <ChevronDown size={20} />
          </div>
        </HeaderRightSlot>
      </HeaderContent>
    </HeaderContainer>
  );
}
