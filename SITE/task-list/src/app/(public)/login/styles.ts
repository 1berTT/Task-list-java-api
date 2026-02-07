import { styled } from "styled-components";

export const Container = styled.form`
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: center;
  width: 100%;
  max-width: 120rem;
  margin: 0 auto;
  height: 100%;
  padding: ${({ theme }) => theme.spacing.lg} ${({ theme }) => theme.spacing.xl};
  gap: ${({ theme }) => theme.spacing.md};

  h1 {
    color: ${({ theme }) => theme.colors.text.primary};
  }
`;
