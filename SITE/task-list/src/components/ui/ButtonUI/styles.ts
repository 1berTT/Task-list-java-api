import styled, { css } from "styled-components";
import type { ButtonVariant } from "./types";

const variants = {
  primary: css`
    background: ${({ theme }) => theme.colors.primary};
    color: ${({ theme }) => theme.colors.text.inverse};
  `,

  secondary: css`
    background: ${({ theme }) => theme.colors.secondary};
    color: ${({ theme }) => theme.colors.text.inverse};
  `,

  tertiary: css`
    background: ${({ theme }) => theme.colors.surface};
    color: ${({ theme }) => theme.colors.primary};
  `,

  danger: css`
    background: ${({ theme }) => theme.colors.danger};
    color: ${({ theme }) => theme.colors.text.inverse};
  `,

  ghost: css`
    background: transparent;
    color: ${({ theme }) => theme.colors.primary};
  `,
};

export const Container = styled.button<{
  variant: ButtonVariant;
}>`
  border: none;
  border-radius: ${({ theme }) => theme.radius.sm};
  font-weight: ${({ theme }) => theme.typography.weights.medium};
  font-size: ${({ theme }) => theme.typography.sizes.sm};
  padding: ${({ theme }) => theme.spacing.sm} ${({ theme }) => theme.spacing.md};
  cursor: pointer;
  transition: 0.2s;

  ${({ variant }) => variants[variant]}

  &:hover {
    filter: brightness(0.9);
  }

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
`;
