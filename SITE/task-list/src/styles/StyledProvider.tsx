"use client";

import type { ReactNode } from "react";

import { ThemeProvider } from "styled-components";
import { theme } from "./theme";
import { GlobalStyle } from "./global";

export function StyledProvider({ children }: { children: ReactNode }) {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      {children}
    </ThemeProvider>
  );
}
