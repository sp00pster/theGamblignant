package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class DiceTechnique extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(DiceTechnique.class.getSimpleName());
    public static final String IMG = makeCardPath("dicetechnique.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 1;

    public DiceTechnique() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int rarityroll = roll(8,'s');
        CardRarity cardRarity;
        if (rarityroll == 1) {
            cardRarity = CardRarity.BASIC;
        } else if (rarityroll <= 4) {
            cardRarity = CardRarity.COMMON;
        } else if (rarityroll <= 7) {
            cardRarity = CardRarity.UNCOMMON;
        } else {
            cardRarity = CardRarity.RARE;
        }
        AbstractCard tmp = CardLibrary.getAnyColorCard(CardType.ATTACK, cardRarity);
        if (this.upgraded) { tmp.setCostForTurn(0); }
        if (AbstractDungeon.player.hand.size() < 10) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(tmp, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        } else {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(tmp, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
