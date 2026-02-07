import { styled } from "styled-components";

export const HeaderContainer = styled.header`
  width: 100%;
  height: 10rem;
  background-color: ${({ theme }) => theme.colors.primary};
`;

export const HeaderContent = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: ${({ theme }) => theme.spacing.lg} ${({ theme }) => theme.spacing.xl};
  max-width: 120rem; /* 1200px */
  margin: 0 auto;
  height: 100%;

  h2 {
    font-size: ${({ theme }) => theme.typography.sizes.xxl};
    font-weight: ${({ theme }) => theme.typography.weights.bold};
    color: ${({ theme }) => theme.colors.text.inverse};
  }
`;

export const HeaderRightSlot = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  min-width: 28rem;

  div.user-info {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    gap: ${({ theme }) => theme.spacing.md};
    padding: ${({ theme }) => theme.spacing.sm} ${({ theme }) => theme.spacing.md};
    border: 0.2rem solid ${({ theme }) => theme.colors.border};
    border-radius: ${({ theme }) => theme.radius.md};
    cursor: pointer;

    &:hover {
      background-color: ${({ theme }) => theme.colors.primaryHover};
    }

    svg {
      flex-shrink: 0;
      color: ${({ theme }) => theme.colors.text.inverse};
      display: flex;
      align-items: center;
      justify-content: center;
    }

    p {
      margin: 0;
      font-size: ${({ theme }) => theme.typography.sizes.sm};
      font-weight: ${({ theme }) => theme.typography.weights.medium};
      color: ${({ theme }) => theme.colors.text.inverse};
      line-height: 1;
    }
  }
`;
