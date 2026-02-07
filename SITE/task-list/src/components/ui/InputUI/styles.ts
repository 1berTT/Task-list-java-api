import styled from "styled-components";

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 6px;
`;

export const Label = styled.label`
  font-size: 14px;
  color: ${({ theme }) => theme.colors.text.secondary};
`;

export const InputWrapper = styled.div<{ $hasError: boolean }>`
  display: flex;
  align-items: center;
  gap: 8px;

  background: ${({ theme }) => theme.colors.surface};
  border: 1px solid
    ${({ theme, $hasError }) =>
      $hasError ? theme.colors.danger : theme.colors.border};

  border-radius: 8px;
  padding: 0 12px;

  &:focus-within {
    border-color: ${({ theme }) => theme.colors.primary};
  }
`;

export const IconContainer = styled.div`
  display: flex;
  align-items: center;
  color: ${({ theme }) => theme.colors.text.secondary};
`;

export const ToggleButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  background: none;
  border: none;
  padding: 0.4rem;
  cursor: pointer;
  color: ${({ theme }) => theme.colors.text.secondary};

  &:hover {
    color: ${({ theme }) => theme.colors.text.primary};
  }
`;

export const Field = styled.input`
  flex: 1;
  border: none;
  background: transparent;
  padding: 10px 0;
  color: ${({ theme }) => theme.colors.text.primary};

  &:focus {
    outline: none;
  }
`;

export const ErrorText = styled.span`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.danger};
`;
